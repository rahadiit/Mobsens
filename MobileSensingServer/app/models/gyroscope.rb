class Gyroscope < ActiveRecord::Base
  belongs_to :recording
  include Sensor
  
  def upload(time, values)
    self.time = Time.strptime(time.to_s, '%Q')
    self.rX = values[0] 
    self.rY = values[1] 
    self.rZ = values[2] 
    self.save
  end 
  
end
