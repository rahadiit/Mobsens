class CreateSensorUploads < ActiveRecord::Migration
  def change
    create_table :sensor_uploads do |t|
      t.references :user, index: true
      t.text :data

      t.timestamps
    end
  end
end
