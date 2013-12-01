class Recording < ActiveRecord::Base
  belongs_to :device
  belongs_to :user

  has_many :accelerometers, :dependent => :destroy, :order => 'time DESC'
  has_many :annotations, :dependent => :destroy, :order => 'time DESC'
  has_many :batteries, :dependent => :destroy, :order => 'time DESC'
  has_many :gravities, :dependent => :destroy, :order => 'time DESC'
  has_many :gyroscopes, :dependent => :destroy, :order => 'time DESC'
  has_many :lights, :dependent => :destroy, :order => 'time DESC'
  has_many :linear_accelerations, :dependent => :destroy, :order => 'time DESC'
  has_many :locations, :dependent => :destroy, :order => 'time DESC'
  has_many :magnetic_fields, :dependent => :destroy, :order => 'time DESC'
  has_many :pressures, :dependent => :destroy, :order => 'time DESC'
  has_many :proximities, :dependent => :destroy, :order => 'time DESC'
  has_many :rotation_vectors, :dependent => :destroy, :order => 'time DESC'

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
  
  def upload(line)
    entry = JSON.parse(line)

    if entry.has_key?('sensor') then
      self.upload_sensor(entry)
      return 
    end
    
    if entry.has_key?('annotation') then
      a = self.annotations.new
      a.upload(entry['annotation']['time'], entry['annotation']['value']) 
      return
    end 

    if entry.has_key?('location') then
      l = self.locations.new
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
      self.save
      return
    end 
  end
  
  def upload_sensor(entry)
      case entry['sensor']['sensor']
      when 1
       a = self.accelerometers.new
       a.upload(entry['sensor']['time'], entry['sensor']['values'])
      when 2
       m = self.magnetic_fields.new
       m.upload(entry['sensor']['time'], entry['sensor']['values'])
      when 4
       g = self.gyroscopes.new
       g.upload(entry['sensor']['time'], entry['sensor']['values'])
      when 5
       l = self.lights.new
       l.upload(entry['sensor']['time'], entry['sensor']['values'])
      when 6
       p = self.pressures.new
       p.upload(entry['sensor']['time'], entry['sensor']['values'])
      when 8
       p = self.proximities.new
       p.upload(entry['sensor']['time'], entry['sensor']['values'])
      when 9
       g = self.gravities.new
       g.upload(entry['sensor']['time'], entry['sensor']['values'])
      when 10
       l = self.linear_accelerations.new
       l.upload(entry['sensor']['time'], entry['sensor']['values'])
      when 11
       r = self.rotation_vectors.new
       r.upload(entry['sensor']['time'], entry['sensor']['values'])
      end
  end 

end
