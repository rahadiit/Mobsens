class CreateRotationVectors < ActiveRecord::Migration
  def change
    create_table :rotation_vectors do |t|
      t.references :recording, index: true
      t.datetime :time
      t.float :xSinTheta
      t.float :ySinTheta
      t.float :zSinTheta

      t.timestamps
    end
  end
end
