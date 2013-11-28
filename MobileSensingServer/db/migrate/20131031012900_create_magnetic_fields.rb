class CreateMagneticFields < ActiveRecord::Migration
  def change
    create_table :magnetic_fields do |t|
      t.references :recording, index: true
      t.datetime :time
      t.float :x
      t.float :y
      t.float :z

      t.timestamps
    end
  end
end
