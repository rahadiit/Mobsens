class RotationVector < ActiveRecord::Base
  belongs_to :recording
  include Sensor

  def upload(time, values)
    self.time = Time.strptime(time.to_s, '%Q')
    self.xSinTheta = values[0]
    self.ySinTheta = values[1]
    self.zSinTheta = values[2]
    self.save
  end
  
end
