class SensorUpload < ActiveRecord::Base
  belongs_to :user
  
  def store_data
    ActiveRecord::Base.uncached do
      rec = Recording.new
      rec.user = self.user;
      rec.save
      self.data.each_line do |line|
        ActiveRecord::Base.transaction do
          rec.upload(line)
        end
      end
    end
  end

end
