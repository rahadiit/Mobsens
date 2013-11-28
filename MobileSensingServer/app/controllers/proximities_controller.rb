class ProximitiesController < ApplicationController
  before_action :set_proximity, only: [:show, :edit, :update, :destroy]

  # GET /proximities
  # GET /proximities.json
  def index
    @proximities = Proximity.all
  end

  # GET /proximities/1
  # GET /proximities/1.json
  def show
  end

  # GET /proximities/new
  def new
    @proximity = Proximity.new
  end

  # GET /proximities/1/edit
  def edit
  end

  # POST /proximities
  # POST /proximities.json
  def create
    @proximity = Proximity.new(proximity_params)

    respond_to do |format|
      if @proximity.save
        format.html { redirect_to @proximity, notice: 'Proximity was successfully created.' }
        format.json { render action: 'show', status: :created, location: @proximity }
      else
        format.html { render action: 'new' }
        format.json { render json: @proximity.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /proximities/1
  # PATCH/PUT /proximities/1.json
  def update
    respond_to do |format|
      if @proximity.update(proximity_params)
        format.html { redirect_to @proximity, notice: 'Proximity was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: 'edit' }
        format.json { render json: @proximity.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /proximities/1
  # DELETE /proximities/1.json
  def destroy
    @proximity.destroy
    respond_to do |format|
      format.html { redirect_to proximities_url }
      format.json { head :no_content }
    end
  end
  
  def list
    recording = Recording.find(params[:id])
    @proximities = recording.proximities
  end
  
  def show_highcharts
    recording = Recording.find(params[:id])
    @sensors = recording.proximities.sample_down(params[:begin], params[:end])
    super
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_proximity
      @proximity = Proximity.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def proximity_params
      params.require(:proximity).permit(:recording_id, :time, :proximity)
    end
end
