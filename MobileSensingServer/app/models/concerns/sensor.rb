module Sensor
  extend ActiveSupport::Concern
  def get_time
    return nil if self.time.nil?
    self.time.strftime('%Y,%m,%d,%H,%M,%S,%L')
  end

  def get_milliseconds
    (self.time.to_f * 1000).to_i
  end

  module ClassMethods
    def sample_down(start, stop)
      result = self
      if start.present? && stop.present?
        result = result.where("time < ? AND time > ?", Time.strptime(stop, '%Q'), Time.strptime(start, '%Q'))
      end
      result = result.limit(100)
      return result
    end

    def get_hz
      return 0 if self.count < 2
      samples = self.order('time')
      time = samples.last.get_milliseconds - samples.first.get_milliseconds
      return 0 if time == 0
      ((samples.count.to_f / time.to_f) * 1000).round(2)
    end

  end

end