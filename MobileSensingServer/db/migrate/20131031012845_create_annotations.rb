class CreateAnnotations < ActiveRecord::Migration
  def change
    create_table :annotations do |t|
      t.references :recording, index: true
      t.datetime :time
      t.string :value

      t.timestamps
    end
  end
end
