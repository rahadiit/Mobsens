class AdminController < ApplicationController

	def sensor_upload_list
		@sensor_upload = SensorUpload.paginate(:page => params[:page])
	end

	def sensor_upload_data
		@sensor_upload = SensorUpload.find(params[:id])
		send_data @sensor_upload.data, :filename => 'raw_data_' + @sensor_upload.id.to_s + '.txt', :type => 'application/text'
	end

	def sensor_upload_import
		@sensor_upload = SensorUpload.find(params[:id])
		@sensor_upload.delay.store_data
		flash[:notice] = 'Upload with ID ' + @sensor_upload.id.to_s + 'imported.'
		redirect_to :controller => 'admin', :action => 'sensor_upload_list'
	end

	def user_list
		@users = User.all
	end

end
