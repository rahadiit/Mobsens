require 'test_helper'

class GyroscopesControllerTest < ActionController::TestCase
  setup do
    @gyroscope = gyroscopes(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:gyroscopes)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create gyroscope" do
    assert_difference('Gyroscope.count') do
      post :create, gyroscope: { rX: @gyroscope.rX, rY: @gyroscope.rY, rZ: @gyroscope.rZ, recording_id: @gyroscope.recording_id, time: @gyroscope.time }
    end

    assert_redirected_to gyroscope_path(assigns(:gyroscope))
  end

  test "should show gyroscope" do
    get :show, id: @gyroscope
    assert_response :success
  end

  test "should get edit" do
    get :edit, id: @gyroscope
    assert_response :success
  end

  test "should update gyroscope" do
    patch :update, id: @gyroscope, gyroscope: { rX: @gyroscope.rX, rY: @gyroscope.rY, rZ: @gyroscope.rZ, recording_id: @gyroscope.recording_id, time: @gyroscope.time }
    assert_redirected_to gyroscope_path(assigns(:gyroscope))
  end

  test "should destroy gyroscope" do
    assert_difference('Gyroscope.count', -1) do
      delete :destroy, id: @gyroscope
    end

    assert_redirected_to gyroscopes_path
  end
end
