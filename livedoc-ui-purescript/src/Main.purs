module Main where

import App (view)
import Control.Monad.Aff (attempt)
import Control.Monad.Aff.Console (log)
import Control.Monad.Eff (Eff)
import Control.Monad.Eff.Class (liftEff)
import Control.Monad.Eff.Console (CONSOLE)
import Control.Monad.Eff.Exception (EXCEPTION, Error)
import DOM (DOM)
import DOM.Event.Event (preventDefault)
import Data.Either (Either)
import Data.Maybe (Maybe(..))
import Network.HTTP.Affjax (AJAX, get)
import Prelude hiding (div)
import Pux (EffModel, start)
import Pux.Renderer.React (renderToDOM)
import Signal.Channel (CHANNEL)
import State (Event(..), State)

defaultUrl :: String
defaultUrl = "http://localhost:2002/static/jsondoc.json"

foldp :: forall fx. Event -> State -> EffModel State Event (console :: CONSOLE, dom :: DOM, ajax :: AJAX | fx)
foldp (FormUpdate s) _ =
  { state: { form: s }
  , effects: [ log ("increment " <> s) *> pure Nothing ]
  }
foldp (FormSubmit ev) s =
  { state: { form: "" }
  , effects: [ liftEff (preventDefault ev) *> log "submit" *> (pure $ Just $ RequestDoc s.form) ]
  }
foldp (RequestDoc url) s =
  { state: s
  , effects: [ do
    res <- attempt $ get url
    log $ "received " <> show ((_.response <$> res) :: Either Error String)
    pure $ Just $ ReceiveDoc "hello"
  ]}
foldp (ReceiveDoc d) s =
  { state: s
  , effects: []
  }

main :: ∀ fx. Eff (channel :: CHANNEL, exception :: EXCEPTION, console :: CONSOLE, dom :: DOM, ajax :: AJAX | fx) Unit
main = do
  app <- start
    { initialState: { form: defaultUrl }
    , view
    , foldp
    , inputs: []
    }

  renderToDOM "#app" app.markup app.input
