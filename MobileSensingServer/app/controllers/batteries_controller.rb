class BatteriesController < ApplicationController
  before_action :set_battery, only: [:show, :edit, :update, :destroy]

  # GET /batteries
  # GET /batteries.json
  def index
    @batteries = Battery.all
  end

  # GET /batteries/1
  # GET /batteries/1.json
  def show
  end

  # GET /batteries/new
  def new
    @battery = Battery.new
  end

  # GET /batteries/1/edit
  def edit
  end

  # POST /batteries
  # POST /batteries.json
  def create
    @battery = Battery.new(battery_params)

    respond_to do |format|
      if @battery.save
        format.html { redirect_to @battery, notice: 'Battery was successfully created.' }
        format.json { render action: 'show', status: :created, location: @battery }
      else
        format.html { render action: 'new' }
        format.json { render json: @battery.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /batteries/1
  # PATCH/PUT /batteries/1.json
  def update
    respond_to do |format|
      if @battery.update(battery_params)
        format.html { redirect_to @battery, notice: 'Battery was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: 'edit' }
        format.json { render json: @battery.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /batteries/1
  # DELETE /batteries/1.json
  def destroy
    @battery.destroy
    respond_to do |format|
      format.html { redirect_to batteries_url }
      format.json { head :no_content }
    end
  end
  
  def list
    recording = Recording.find(params[:id])
    @batteries = recording.batteries
  end
  
  def show_highcharts
    recording = Recording.find(params[:id])
    @sensors = recording.batteries.sample_down(params[:begin], params[:end])
    super
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_battery
      @battery = Battery.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def battery_params
      params.require(:battery).permit(:recording_id, :time, :level, :scale)
    end
end
