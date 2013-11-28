class Annotation < ActiveRecord::Base
  belongs_to :recording
  include Sensor
  
  def upload(time, value)
    self.time = Time.strptime(time.to_s, '%Q')
    self.value = value
    self.save
  end

end
