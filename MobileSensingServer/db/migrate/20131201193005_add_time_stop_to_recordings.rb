class AddTimeStopToRecordings < ActiveRecord::Migration
  def change
    add_column :recordings, :time_stop, :datetime
  end
end
