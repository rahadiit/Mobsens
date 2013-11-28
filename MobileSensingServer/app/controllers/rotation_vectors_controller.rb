class RotationVectorsController < ApplicationController
  before_action :set_rotation_vector, only: [:show, :edit, :update, :destroy]

  # GET /rotation_vectors
  # GET /rotation_vectors.json
  def index
    @rotation_vectors = RotationVector.all
  end

  # GET /rotation_vectors/1
  # GET /rotation_vectors/1.json
  def show
  end

  # GET /rotation_vectors/new
  def new
    @rotation_vector = RotationVector.new
  end

  # GET /rotation_vectors/1/edit
  def edit
  end

  # POST /rotation_vectors
  # POST /rotation_vectors.json
  def create
    @rotation_vector = RotationVector.new(rotation_vector_params)

    respond_to do |format|
      if @rotation_vector.save
        format.html { redirect_to @rotation_vector, notice: 'Rotation vector was successfully created.' }
        format.json { render action: 'show', status: :created, location: @rotation_vector }
      else
        format.html { render action: 'new' }
        format.json { render json: @rotation_vector.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /rotation_vectors/1
  # PATCH/PUT /rotation_vectors/1.json
  def update
    respond_to do |format|
      if @rotation_vector.update(rotation_vector_params)
        format.html { redirect_to @rotation_vector, notice: 'Rotation vector was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: 'edit' }
        format.json { render json: @rotation_vector.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /rotation_vectors/1
  # DELETE /rotation_vectors/1.json
  def destroy
    @rotation_vector.destroy
    respond_to do |format|
      format.html { redirect_to rotation_vectors_url }
      format.json { head :no_content }
    end
  end
  
  def list
    recording = Recording.find(params[:id])
    @rotation_vectors = recording.rotation_vectors
  end
 
  def show_highcharts
    recording = Recording.find(params[:id])
    @sensors = recording.rotation_vectors.sample_down(params[:begin], params[:end])
    super
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_rotation_vector
      @rotation_vector = RotationVector.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def rotation_vector_params
      params.require(:rotation_vector).permit(:recording_id, :time, :xSinTheta, :ySinTheta, :zSinTheta)
    end
end
