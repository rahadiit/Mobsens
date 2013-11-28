require 'test_helper'

class ProximitiesControllerTest < ActionController::TestCase
  setup do
    @proximity = proximities(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:proximities)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create proximity" do
    assert_difference('Proximity.count') do
      post :create, proximity: { proximity: @proximity.proximity, recording_id: @proximity.recording_id, time: @proximity.time }
    end

    assert_redirected_to proximity_path(assigns(:proximity))
  end

  test "should show proximity" do
    get :show, id: @proximity
    assert_response :success
  end

  test "should get edit" do
    get :edit, id: @proximity
    assert_response :success
  end

  test "should update proximity" do
    patch :update, id: @proximity, proximity: { proximity: @proximity.proximity, recording_id: @proximity.recording_id, time: @proximity.time }
    assert_redirected_to proximity_path(assigns(:proximity))
  end

  test "should destroy proximity" do
    assert_difference('Proximity.count', -1) do
      delete :destroy, id: @proximity
    end

    assert_redirected_to proximities_path
  end
end
