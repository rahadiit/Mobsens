require 'test_helper'

class LinearAccelerationsControllerTest < ActionController::TestCase
  setup do
    @linear_acceleration = linear_accelerations(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:linear_accelerations)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create linear_acceleration" do
    assert_difference('LinearAcceleration.count') do
      post :create, linear_acceleration: { recording_id: @linear_acceleration.recording_id, time: @linear_acceleration.time, x: @linear_acceleration.x, y: @linear_acceleration.y, z: @linear_acceleration.z }
    end

    assert_redirected_to linear_acceleration_path(assigns(:linear_acceleration))
  end

  test "should show linear_acceleration" do
    get :show, id: @linear_acceleration
    assert_response :success
  end

  test "should get edit" do
    get :edit, id: @linear_acceleration
    assert_response :success
  end

  test "should update linear_acceleration" do
    patch :update, id: @linear_acceleration, linear_acceleration: { recording_id: @linear_acceleration.recording_id, time: @linear_acceleration.time, x: @linear_acceleration.x, y: @linear_acceleration.y, z: @linear_acceleration.z }
    assert_redirected_to linear_acceleration_path(assigns(:linear_acceleration))
  end

  test "should destroy linear_acceleration" do
    assert_difference('LinearAcceleration.count', -1) do
      delete :destroy, id: @linear_acceleration
    end

    assert_redirected_to linear_accelerations_path
  end
end
