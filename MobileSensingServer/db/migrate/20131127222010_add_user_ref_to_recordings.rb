class AddUserRefToRecordings < ActiveRecord::Migration
  def change
    add_reference :recordings, :user, index: true
  end
end
