class CreateLocations < ActiveRecord::Migration
  def change
    create_table :locations do |t|
      t.references :recording, index: true
      t.datetime :time
      t.float :longitude
      t.float :latitude
      t.float :speed
      t.float :bearing
      t.float :accuracy

      t.timestamps
    end
  end
end
