class SensorUpload < ActiveRecord::Base
  belongs_to :user
  
 def store_data 
    rec = Recording.new
    rec.user = self.user;
    rec.save
    self.data.each_line do |line|
      rec.upload(line)  
    end
  end
  
end
