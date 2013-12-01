class AdminController < ApplicationController
  
  def sensor_upload_list 
    @sensor_upload = SensorUpload.all  
  end
   
  def sensor_upload_data
    @sensor_upload = SensorUpload.find(params[:id])
    send_data @sensor_upload.data, :filename => 'raw_data_' + @sensor_upload.id.to_s + '.txt', :type => 'application/text'
  end
    
  def user_list 
    @users = User.all  
  end
  
end