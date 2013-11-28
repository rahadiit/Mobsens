require 'test_helper'

class RotationVectorsControllerTest < ActionController::TestCase
  setup do
    @rotation_vector = rotation_vectors(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:rotation_vectors)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create rotation_vector" do
    assert_difference('RotationVector.count') do
      post :create, rotation_vector: { recording_id: @rotation_vector.recording_id, time: @rotation_vector.time, xSinTheta: @rotation_vector.xSinTheta, ySinTheta: @rotation_vector.ySinTheta, zSinTheta: @rotation_vector.zSinTheta }
    end

    assert_redirected_to rotation_vector_path(assigns(:rotation_vector))
  end

  test "should show rotation_vector" do
    get :show, id: @rotation_vector
    assert_response :success
  end

  test "should get edit" do
    get :edit, id: @rotation_vector
    assert_response :success
  end

  test "should update rotation_vector" do
    patch :update, id: @rotation_vector, rotation_vector: { recording_id: @rotation_vector.recording_id, time: @rotation_vector.time, xSinTheta: @rotation_vector.xSinTheta, ySinTheta: @rotation_vector.ySinTheta, zSinTheta: @rotation_vector.zSinTheta }
    assert_redirected_to rotation_vector_path(assigns(:rotation_vector))
  end

  test "should destroy rotation_vector" do
    assert_difference('RotationVector.count', -1) do
      delete :destroy, id: @rotation_vector
    end

    assert_redirected_to rotation_vectors_path
  end
end
