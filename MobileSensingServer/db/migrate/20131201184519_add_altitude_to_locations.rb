class AddAltitudeToLocations < ActiveRecord::Migration
  def change
    add_column :locations, :altitude, :float
  end
end
