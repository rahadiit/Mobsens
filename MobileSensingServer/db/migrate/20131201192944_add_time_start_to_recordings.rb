class AddTimeStartToRecordings < ActiveRecord::Migration
  def change
    add_column :recordings, :time_start, :datetime
  end
end
