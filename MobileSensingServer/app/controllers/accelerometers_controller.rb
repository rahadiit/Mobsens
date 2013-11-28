class AccelerometersController < ApplicationController
  before_action :set_accelerometer, only: [:show, :edit, :update, :destroy]
  # GET /accelerometers
  # GET /accelerometers.json
  def index
    @accelerometers = Accelerometer.all
  end

  # GET /accelerometers/1
  # GET /accelerometers/1.json
  def show
  end

  # GET /accelerometers/new
  def new
    @accelerometer = Accelerometer.new
  end

  # GET /accelerometers/1/edit
  def edit
  end

  # POST /accelerometers
  # POST /accelerometers.json
  def create
    @accelerometer = Accelerometer.new(accelerometer_params)

    respond_to do |format|
      if @accelerometer.save
        format.html { redirect_to @accelerometer, notice: 'Accelerometer was successfully created.' }
        format.json { render action: 'show', status: :created, location: @accelerometer }
      else
        format.html { render action: 'new' }
        format.json { render json: @accelerometer.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /accelerometers/1
  # PATCH/PUT /accelerometers/1.json
  def update
    respond_to do |format|
      if @accelerometer.update(accelerometer_params)
        format.html { redirect_to @accelerometer, notice: 'Accelerometer was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: 'edit' }
        format.json { render json: @accelerometer.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /accelerometers/1
  # DELETE /accelerometers/1.json
  def destroy
    @accelerometer.destroy
    respond_to do |format|
      format.html { redirect_to accelerometers_url }
      format.json { head :no_content }
    end
  end
  
  def list
    recording = Recording.find(params[:id])
    @accelerometers = recording.accelerometers
  end

  def show_highcharts
    recording = Recording.find(params[:id])
    @sensors = recording.accelerometers.sample_down(params[:begin], params[:end])
    super
  end

  private

  # Use callbacks to share common setup or constraints between actions.
  def set_accelerometer
    @accelerometer = Accelerometer.find(params[:id])
  end

  # Never trust parameters from the scary internet, only allow the white list through.
  def accelerometer_params
    params.require(:accelerometer).permit(:recording_id, :time, :x, :y, :z)
  end
end
