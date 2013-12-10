class GyroscopesController < ApplicationController
  before_action :set_gyroscope, only: [:show, :edit, :update, :destroy]

  # GET /gyroscopes
  # GET /gyroscopes.json
  def index
    @gyroscopes = Gyroscope.all
  end

  # GET /gyroscopes/1
  # GET /gyroscopes/1.json
  def show
  end

  # GET /gyroscopes/new
  def new
    @gyroscope = Gyroscope.new
  end

  # GET /gyroscopes/1/edit
  def edit
  end

  # POST /gyroscopes
  # POST /gyroscopes.json
  def create
    @gyroscope = Gyroscope.new(gyroscope_params)

    respond_to do |format|
      if @gyroscope.save
        format.html { redirect_to @gyroscope, notice: 'Gyroscope was successfully created.' }
        format.json { render action: 'show', status: :created, location: @gyroscope }
      else
        format.html { render action: 'new' }
        format.json { render json: @gyroscope.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /gyroscopes/1
  # PATCH/PUT /gyroscopes/1.json
  def update
    respond_to do |format|
      if @gyroscope.update(gyroscope_params)
        format.html { redirect_to @gyroscope, notice: 'Gyroscope was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: 'edit' }
        format.json { render json: @gyroscope.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /gyroscopes/1
  # DELETE /gyroscopes/1.json
  def destroy
    @gyroscope.destroy
    respond_to do |format|
      format.html { redirect_to gyroscopes_url }
      format.json { head :no_content }
    end
  end

  def list
    recording = Recording.find(params[:id])
    @gyroscopes = recording.gyroscopes
  end

  def show_highcharts
    recording = Recording.find(params[:id])
    @sensors = recording.gyroscopes.sample_down(params[:begin], params[:end])
    super
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_gyroscope
      @gyroscope = Gyroscope.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def gyroscope_params
      params.require(:gyroscope).permit(:recording_id, :time, :rX, :rY, :rZ)
    end
end
