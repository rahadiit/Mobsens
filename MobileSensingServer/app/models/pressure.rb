class Pressure < ActiveRecord::Base
  belongs_to :recording
  include Sensor
  
  def upload(time, values)
    self.time = Time.strptime(time.to_s, '%Q')
    self.pressure = values[0]
    self.save
  end

end
