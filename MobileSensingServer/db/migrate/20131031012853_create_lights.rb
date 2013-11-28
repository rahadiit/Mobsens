class CreateLights < ActiveRecord::Migration
  def change
    create_table :lights do |t|
      t.references :recording, index: true
      t.datetime :time
      t.float :ambientLight

      t.timestamps
    end
  end
end
