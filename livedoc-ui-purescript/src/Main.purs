module Main where

import App (view)
import Control.Monad.Aff.Console (log)
import Control.Monad.Eff (Eff)
import Control.Monad.Eff.Class (liftEff)
import Control.Monad.Eff.Console (CONSOLE)
import Control.Monad.Eff.Exception (EXCEPTION)
import DOM (DOM)
import DOM.Event.Event (preventDefault)
import Data.Maybe (Maybe(..))
import Prelude hiding (div)
import Pux (EffModel, start)
import Pux.Renderer.React (renderToDOM)
import Signal.Channel (CHANNEL)
import State (Event(..), State)

foldp :: forall fx. Event -> State -> EffModel State Event (console :: CONSOLE, dom :: DOM | fx)
foldp (FormUpdate s) _ =
  { state: { form: s }
  , effects: [ log ("increment " <> s) *> pure Nothing ]
  }
foldp (FormSubmit ev) _ =
  { state: { form: "" }
  , effects: [ liftEff (preventDefault ev) *> log "submit" *> pure Nothing ]
  }

main :: ∀ fx. Eff (channel :: CHANNEL, exception :: EXCEPTION, console :: CONSOLE, dom :: DOM | fx) Unit
main = do
  app <- start
    { initialState: { form: "" }
    , view
    , foldp
    , inputs: []
    }

  renderToDOM "#app" app.markup app.input
