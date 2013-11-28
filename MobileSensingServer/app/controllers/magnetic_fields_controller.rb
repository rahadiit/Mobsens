class MagneticFieldsController < ApplicationController
  before_action :set_magnetic_field, only: [:show, :edit, :update, :destroy]

  # GET /magnetic_fields
  # GET /magnetic_fields.json
  def index
    @magnetic_fields = MagneticField.all
  end

  # GET /magnetic_fields/1
  # GET /magnetic_fields/1.json
  def show
  end

  # GET /magnetic_fields/new
  def new
    @magnetic_field = MagneticField.new
  end

  # GET /magnetic_fields/1/edit
  def edit
  end

  # POST /magnetic_fields
  # POST /magnetic_fields.json
  def create
    @magnetic_field = MagneticField.new(magnetic_field_params)

    respond_to do |format|
      if @magnetic_field.save
        format.html { redirect_to @magnetic_field, notice: 'Magnetic field was successfully created.' }
        format.json { render action: 'show', status: :created, location: @magnetic_field }
      else
        format.html { render action: 'new' }
        format.json { render json: @magnetic_field.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /magnetic_fields/1
  # PATCH/PUT /magnetic_fields/1.json
  def update
    respond_to do |format|
      if @magnetic_field.update(magnetic_field_params)
        format.html { redirect_to @magnetic_field, notice: 'Magnetic field was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: 'edit' }
        format.json { render json: @magnetic_field.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /magnetic_fields/1
  # DELETE /magnetic_fields/1.json
  def destroy
    @magnetic_field.destroy
    respond_to do |format|
      format.html { redirect_to magnetic_fields_url }
      format.json { head :no_content }
    end
  end
  
  def list
    recording = Recording.find(params[:id])
    @magnetic_fields = recording.magnetic_fields
  end
  
  def show_highcharts
    recording = Recording.find(params[:id])
    @sensors = recording.magnetic_fields.sample_down(params[:begin], params[:end])
    super
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_magnetic_field
      @magnetic_field = MagneticField.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def magnetic_field_params
      params.require(:magnetic_field).permit(:recording_id, :time, :x, :y, :z)
    end
end
