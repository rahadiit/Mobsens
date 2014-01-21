class RecordingsController < ApplicationController
  before_action :set_recording, only: [:show, :edit, :update, :destroy]
  before_filter :handle_gzip_params

  # GET /recordings
  # GET /recordings.json
  def index
    @recordings = Recording.paginate(:page => params[:page])
  end

  # GET /recordings/1
  # GET /recordings/1.json
  def show
  end

  # GET /recordings/new
  def new
    @recording = Recording.new
  end

  # GET /recordings/1/edit
  def edit
  end

  # POST /recordings
  # POST /recordings.json
  def create
    @recording = Recording.new(recording_params)

    respond_to do |format|
      if @recording.save
        format.html { redirect_to @recording, notice: 'Recording was successfully created.' }
        format.json { render action: 'show', status: :created, location: @recording }
      else
        format.html { render action: 'new' }
        format.json { render json: @recording.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /recordings/1
  # PATCH/PUT /recordings/1.json
  def update
    respond_to do |format|
      if @recording.update(recording_params)
        format.html { redirect_to @recording, notice: 'Recording was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: 'edit' }
        format.json { render json: @recording.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /recordings/1
  # DELETE /recordings/1.json
  def destroy
    @recording.destroy
    respond_to do |format|
      format.html { redirect_to recordings_url, notice: 'Recording was successfully deleted'  }
      format.json { head :no_content }
    end
  end

  def upload
    
    upload = SensorUpload.new
    upload.user = current_user
    upload.data = request.raw_post
    upload.save
    
    upload.delay.store_data
    
   render :nothing => true
  end
  
  private

  # Use callbacks to share common setup or constraints between actions.
  def set_recording
    @recording = Recording.find(params[:id])
  end

  # Never trust parameters from the scary internet, only allow the white list through.
  def recording_params
    params.require(:recording).permit(:device_id, :title,
    :accelerometers_attributes => [:recording_id, :time, :x, :y, :z],
    :annotations_attributes => [:recording_id, :time, :value],
    :batteries_attributes => [:recording_id, :time, :level, :scale],
    :devices_attributes => [:identifier],
    :gravities_attributes => [:recording_id, :time, :x, :y, :z],
    :gyroscopes_attributes => [:recording_id, :time, :rX, :rY, :rZ],
    :lights_attributes => [:recording_id, :time, :ambientLight],
    :linear_accelerations_attributes => [:recording_id, :time, :x, :y, :z],
    :locations_attributes => [:time, :longitude, :latitude, :altitude, :speed, :bearing, :accuracy],
    :magnetic_fields_attributes => [:recording_id, :time, :x, :y, :z],
    :pressures_attributes => [:recording_id, :time, :pressure],
    :proximities_attributes => [:recording_id, :time, :proximity],
    :rotation_vectors_attributes => [:recording_id, :time, :xSinTheta, :ySinTheta, :zSinTheta])
  end
end
