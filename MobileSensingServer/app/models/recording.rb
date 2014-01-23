class Recording < ActiveRecord::Base

  self.per_page = 10

  default_scope :order => 'time_start DESC'

  belongs_to :device
  belongs_to :user

  has_many :accelerometers, :dependent => :delete_all, :order => 'time ASC'
  has_many :annotations, :dependent => :delete_all, :order => 'time ASC'
  has_many :batteries, :dependent => :delete_all, :order => 'time ASC'
  has_many :gravities, :dependent => :delete_all, :order => 'time ASC'
  has_many :gyroscopes, :dependent => :delete_all, :order => 'time ASC'
  has_many :lights, :dependent => :delete_all, :order => 'time ASC'
  has_many :linear_accelerations, :dependent => :delete_all, :order => 'time ASC'
  has_many :locations, :dependent => :delete_all, :order => 'time ASC'
  has_many :magnetic_fields, :dependent => :delete_all, :order => 'time ASC'
  has_many :pressures, :dependent => :delete_all, :order => 'time ASC'
  has_many :proximities, :dependent => :delete_all, :order => 'time ASC'
  has_many :rotation_vectors, :dependent => :delete_all, :order => 'time ASC'

  accepts_nested_attributes_for :accelerometers
  accepts_nested_attributes_for :annotations
  accepts_nested_attributes_for :batteries
  accepts_nested_attributes_for :gravities
  accepts_nested_attributes_for :gyroscopes
  accepts_nested_attributes_for :lights
  accepts_nested_attributes_for :linear_accelerations
  accepts_nested_attributes_for :locations
  accepts_nested_attributes_for :magnetic_fields
  accepts_nested_attributes_for :pressures
  accepts_nested_attributes_for :proximities
  accepts_nested_attributes_for :rotation_vectors
  
  def get_duration
    return nil if self.time_start.nil?
    return nil if self.time_stop.nil?
    time_difference = Time.diff(self.time_start, self.time_stop, '%y, %d and %h:%m:%s')[:diff]
  end

  def classify
    Rjb::load('/home/lukas/projects/uni/forschungspraktikum/MobileSensors.jar', ['-verbose:gc'])
    loc = Rjb::import('MobileSensors.Storage.GPS.Location')
    l = loc.new(1,2,3,4,5,6,7)
    l.getTime()
  end

  def upload(line)
    entry = JSON.parse(line)

    if entry.has_key?('sensor') then
    self.upload_sensor(entry)
    return
    end

    if entry.has_key?('annotation') then
      a = Annotation.new 
      a.recording = self
      a.upload(entry['annotation']['time'], entry['annotation']['value'])
    return
    end

    if entry.has_key?('location') then
      l = Location.new
      l.recording = self
      l.upload(entry['location']['time'], entry['location'])
    return
    end

    if entry.has_key?('rec') then
      if entry['rec'].has_key?('did') then
        d = Device.find_or_create_by(:identifier => entry['rec']['did'])
        self.device = d
      end
      if entry['rec'].has_key?('title') then
        self.title = entry['rec']['title']
      end
      if entry['rec'].has_key?('time_start') then
        self.time_start = Time.strptime(entry['rec']['time_start'].to_s, '%Q')
      end
      if entry['rec'].has_key?('time_stop') then
        self.time_stop = Time.strptime(entry['rec']['time_stop'].to_s, '%Q')
      end
    self.save
    return
    end
  end

  def upload_sensor(entry)
    case entry['sensor']['sensor']
    when 1
      a = Accelerometer.new
      a.recording = self
      a.upload(entry['sensor']['time'], entry['sensor']['values'])
    when 2
      m = MagneticField.new
      m.recording =  self
      m.upload(entry['sensor']['time'], entry['sensor']['values'])
    when 4
      g = Gyroscope.new
      g.recording = self
      g.upload(entry['sensor']['time'], entry['sensor']['values'])
    when 5
      l = Light.new
      l.recording = self
      l.upload(entry['sensor']['time'], entry['sensor']['values'])
    when 6
      p = Pressure.new
      p.recording = self
      p.upload(entry['sensor']['time'], entry['sensor']['values'])
    when 8
      p = Proximity.new
      p.recording = self
      p.upload(entry['sensor']['time'], entry['sensor']['values'])
    when 9
      g = Gravity.new
      g.recording = self
      g.upload(entry['sensor']['time'], entry['sensor']['values'])
    when 10
      l = LinearAcceleration.new
      l.recording = self
      l.upload(entry['sensor']['time'], entry['sensor']['values'])
    when 11
      r = RotationVector.new
      r.recording = self
      r.upload(entry['sensor']['time'], entry['sensor']['values'])
    end
  end

end
