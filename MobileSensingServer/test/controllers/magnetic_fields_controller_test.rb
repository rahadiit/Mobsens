require 'test_helper'

class MagneticFieldsControllerTest < ActionController::TestCase
  setup do
    @magnetic_field = magnetic_fields(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:magnetic_fields)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create magnetic_field" do
    assert_difference('MagneticField.count') do
      post :create, magnetic_field: { recording_id: @magnetic_field.recording_id, time: @magnetic_field.time, x: @magnetic_field.x, y: @magnetic_field.y, z: @magnetic_field.z }
    end

    assert_redirected_to magnetic_field_path(assigns(:magnetic_field))
  end

  test "should show magnetic_field" do
    get :show, id: @magnetic_field
    assert_response :success
  end

  test "should get edit" do
    get :edit, id: @magnetic_field
    assert_response :success
  end

  test "should update magnetic_field" do
    patch :update, id: @magnetic_field, magnetic_field: { recording_id: @magnetic_field.recording_id, time: @magnetic_field.time, x: @magnetic_field.x, y: @magnetic_field.y, z: @magnetic_field.z }
    assert_redirected_to magnetic_field_path(assigns(:magnetic_field))
  end

  test "should destroy magnetic_field" do
    assert_difference('MagneticField.count', -1) do
      delete :destroy, id: @magnetic_field
    end

    assert_redirected_to magnetic_fields_path
  end
end
