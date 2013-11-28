class MagneticField < ActiveRecord::Base
  belongs_to :recording
  include Sensor
  
  def upload(time, values)
    self.time = Time.strptime(time.to_s, '%Q')
    self.x = values[0] 
    self.y = values[1] 
    self.z = values[2] 
    self.save
  end
  
end
