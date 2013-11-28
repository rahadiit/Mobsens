class CreatePressures < ActiveRecord::Migration
  def change
    create_table :pressures do |t|
      t.references :recording, index: true
      t.datetime :time
      t.float :pressure

      t.timestamps
    end
  end
end
