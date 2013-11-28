class CreateBatteries < ActiveRecord::Migration
  def change
    create_table :batteries do |t|
      t.references :recording, index: true
      t.datetime :time
      t.integer :level
      t.integer :scale

      t.timestamps
    end
  end
end
