class Location < ActiveRecord::Base
  belongs_to :recording
  include Sensor
  
  def upload(time, location)
    self.time = Time.strptime(time.to_s, '%Q')
    self.longitude = location['longitude'] 
    self.latitude = location['latitude'] 
    self.speed = location['speed'] 
    self.bearing = location['bearing'] 
    self.accuracy = location['accuracy'] 
    self.save
  end
end
