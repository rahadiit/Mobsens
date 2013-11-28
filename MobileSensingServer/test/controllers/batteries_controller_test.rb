require 'test_helper'

class BatteriesControllerTest < ActionController::TestCase
  setup do
    @battery = batteries(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:batteries)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create battery" do
    assert_difference('Battery.count') do
      post :create, battery: { level: @battery.level, recording_id: @battery.recording_id, scale: @battery.scale, time: @battery.time }
    end

    assert_redirected_to battery_path(assigns(:battery))
  end

  test "should show battery" do
    get :show, id: @battery
    assert_response :success
  end

  test "should get edit" do
    get :edit, id: @battery
    assert_response :success
  end

  test "should update battery" do
    patch :update, id: @battery, battery: { level: @battery.level, recording_id: @battery.recording_id, scale: @battery.scale, time: @battery.time }
    assert_redirected_to battery_path(assigns(:battery))
  end

  test "should destroy battery" do
    assert_difference('Battery.count', -1) do
      delete :destroy, id: @battery
    end

    assert_redirected_to batteries_path
  end
end
