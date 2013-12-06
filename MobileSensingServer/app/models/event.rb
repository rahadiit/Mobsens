class Event < ActiveRecord::Base
  belongs_to :recording
  belongs_to :event_type
end
