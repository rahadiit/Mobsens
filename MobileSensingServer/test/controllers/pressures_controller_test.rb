require 'test_helper'

class PressuresControllerTest < ActionController::TestCase
  setup do
    @pressure = pressures(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:pressures)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create pressure" do
    assert_difference('Pressure.count') do
      post :create, pressure: { pressure: @pressure.pressure, recording_id: @pressure.recording_id, time: @pressure.time }
    end

    assert_redirected_to pressure_path(assigns(:pressure))
  end

  test "should show pressure" do
    get :show, id: @pressure
    assert_response :success
  end

  test "should get edit" do
    get :edit, id: @pressure
    assert_response :success
  end

  test "should update pressure" do
    patch :update, id: @pressure, pressure: { pressure: @pressure.pressure, recording_id: @pressure.recording_id, time: @pressure.time }
    assert_redirected_to pressure_path(assigns(:pressure))
  end

  test "should destroy pressure" do
    assert_difference('Pressure.count', -1) do
      delete :destroy, id: @pressure
    end

    assert_redirected_to pressures_path
  end
end
