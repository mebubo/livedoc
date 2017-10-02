module View.InlineForm where

import Prelude (discard, ($))
import Pux.DOM.Events (onChange, targetValue, onSubmit)
import Pux.DOM.HTML (HTML)
import State (Event(..), State)
import Text.Smolder.HTML (button, form, input)
import Text.Smolder.HTML.Attributes (type', value)
import Text.Smolder.Markup (text, (!), (#!))

view :: State -> HTML Event
view state = form #! onSubmit FormSubmit $ do
  input ! type' "text" ! value state.form #! onChange (\ev -> FormUpdate (targetValue ev))
  button $ text "Get Doc"
