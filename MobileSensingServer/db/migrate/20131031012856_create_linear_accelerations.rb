class CreateLinearAccelerations < ActiveRecord::Migration
  def change
    create_table :linear_accelerations do |t|
      t.references :recording, index: true
      t.datetime :time
      t.float :x
      t.float :y
      t.float :z

      t.timestamps
    end
  end
end
