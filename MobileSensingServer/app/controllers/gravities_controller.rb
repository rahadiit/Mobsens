class GravitiesController < ApplicationController
  before_action :set_gravity, only: [:show, :edit, :update, :destroy]

  # GET /gravities
  # GET /gravities.json
  def index
    @gravities = Gravity.all
  end

  # GET /gravities/1
  # GET /gravities/1.json
  def show
  end

  # GET /gravities/new
  def new
    @gravity = Gravity.new
  end

  # GET /gravities/1/edit
  def edit
  end

  # POST /gravities
  # POST /gravities.json
  def create
    @gravity = Gravity.new(gravity_params)

    respond_to do |format|
      if @gravity.save
        format.html { redirect_to @gravity, notice: 'Gravity was successfully created.' }
        format.json { render action: 'show', status: :created, location: @gravity }
      else
        format.html { render action: 'new' }
        format.json { render json: @gravity.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /gravities/1
  # PATCH/PUT /gravities/1.json
  def update
    respond_to do |format|
      if @gravity.update(gravity_params)
        format.html { redirect_to @gravity, notice: 'Gravity was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: 'edit' }
        format.json { render json: @gravity.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /gravities/1
  # DELETE /gravities/1.json
  def destroy
    @gravity.destroy
    respond_to do |format|
      format.html { redirect_to gravities_url }
      format.json { head :no_content }
    end
  end
  
  def list
    recording = Recording.find(params[:id])
    @gravities = recording.gravities
  end
  
  def show_highcharts
    recording = Recording.find(params[:id])
    @sensors = recording.gravities.sample_down(params[:begin], params[:end])
    super
  end
  
  private
    # Use callbacks to share common setup or constraints between actions.
    def set_gravity
      @gravity = Gravity.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def gravity_params
      params.require(:gravity).permit(:recording_id, :time, :x, :y, :z)
    end
end
