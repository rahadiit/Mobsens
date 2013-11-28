class CreateRecordings < ActiveRecord::Migration
  def change
    create_table :recordings do |t|
      t.references :device, index: true

      t.timestamps
    end
  end
end
