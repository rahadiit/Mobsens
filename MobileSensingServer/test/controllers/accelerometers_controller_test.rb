require 'test_helper'

class AccelerometersControllerTest < ActionController::TestCase
  setup do
    @accelerometer = accelerometers(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:accelerometers)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create accelerometer" do
    assert_difference('Accelerometer.count') do
      post :create, accelerometer: { recording_id: @accelerometer.recording_id, time: @accelerometer.time, x: @accelerometer.x, y: @accelerometer.y, z: @accelerometer.z }
    end

    assert_redirected_to accelerometer_path(assigns(:accelerometer))
  end

  test "should show accelerometer" do
    get :show, id: @accelerometer
    assert_response :success
  end

  test "should get edit" do
    get :edit, id: @accelerometer
    assert_response :success
  end

  test "should update accelerometer" do
    patch :update, id: @accelerometer, accelerometer: { recording_id: @accelerometer.recording_id, time: @accelerometer.time, x: @accelerometer.x, y: @accelerometer.y, z: @accelerometer.z }
    assert_redirected_to accelerometer_path(assigns(:accelerometer))
  end

  test "should destroy accelerometer" do
    assert_difference('Accelerometer.count', -1) do
      delete :destroy, id: @accelerometer
    end

    assert_redirected_to accelerometers_path
  end
end
