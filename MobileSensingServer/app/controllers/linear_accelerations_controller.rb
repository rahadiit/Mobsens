class LinearAccelerationsController < ApplicationController
  before_action :set_linear_acceleration, only: [:show, :edit, :update, :destroy]

  # GET /linear_accelerations
  # GET /linear_accelerations.json
  def index
    @linear_accelerations = LinearAcceleration.all
  end

  # GET /linear_accelerations/1
  # GET /linear_accelerations/1.json
  def show
  end

  # GET /linear_accelerations/new
  def new
    @linear_acceleration = LinearAcceleration.new
  end

  # GET /linear_accelerations/1/edit
  def edit
  end

  # POST /linear_accelerations
  # POST /linear_accelerations.json
  def create
    @linear_acceleration = LinearAcceleration.new(linear_acceleration_params)

    respond_to do |format|
      if @linear_acceleration.save
        format.html { redirect_to @linear_acceleration, notice: 'Linear acceleration was successfully created.' }
        format.json { render action: 'show', status: :created, location: @linear_acceleration }
      else
        format.html { render action: 'new' }
        format.json { render json: @linear_acceleration.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /linear_accelerations/1
  # PATCH/PUT /linear_accelerations/1.json
  def update
    respond_to do |format|
      if @linear_acceleration.update(linear_acceleration_params)
        format.html { redirect_to @linear_acceleration, notice: 'Linear acceleration was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: 'edit' }
        format.json { render json: @linear_acceleration.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /linear_accelerations/1
  # DELETE /linear_accelerations/1.json
  def destroy
    @linear_acceleration.destroy
    respond_to do |format|
      format.html { redirect_to linear_accelerations_url }
      format.json { head :no_content }
    end
  end

  def list
    recording = Recording.find(params[:id])
    @linear_accelerations = recording.linear_accelerations
  end
  
  def show_highcharts
    recording = Recording.find(params[:id])
    @sensors = recording.linear_accelerations.sample_down(params[:begin], params[:end])
    super
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_linear_acceleration
      @linear_acceleration = LinearAcceleration.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def linear_acceleration_params
      params.require(:linear_acceleration).permit(:recording_id, :time, :x, :y, :z)
    end
end
