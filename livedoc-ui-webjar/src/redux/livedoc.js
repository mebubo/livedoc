// @flow
import type { ApiDoc, ApiFlowDoc, ApiGlobalDoc, ApiObjectDoc, Identified, Livedoc, LivedocID } from '../model/livedoc';
import type { State } from '../model/state';
import type { Action } from './loader';
import { types } from './loader';

export default (livedoc: ?Livedoc = null, action: Action) => {
  switch (action.type) {
    case types.DOC_FETCHED:
      return action.livedoc;
    case types.FETCH_DOC:
    case types.DOC_FETCH_ERROR:
    case types.RESET:
      return null;
    default:
      return livedoc;
  }
};

type ElementArray<T> = $ReadOnlyArray<T & Identified>;
type ElementsById<T> = { [id: LivedocID]: T };

const dictionarize = <T>(elementsByWhatever: { [string]: ElementArray<T> }): ElementsById<T> => {
  const arraysOfElements: Array<ElementArray<T>> = Object.keys(elementsByWhatever).map(k => elementsByWhatever[k]);
  return arraysOfElements.reduce((elemsById: ElementsById<T>, elems: ElementArray<T>) => {
    elems.forEach(e => {
      elemsById[e.livedocId] = e;
    });
    return elemsById;
  }, {});
};

const getElementById = <T>(id: ?LivedocID, elements: { [string]: ElementArray<T> }): ?T => {
  if (!id) {
    return null;
  }
  const elementsById = dictionarize(elements);
  return elementsById[id];
};

export function getGlobalDoc(state: State): ?ApiGlobalDoc {
  return state.livedoc && state.livedoc.global;
}

export function getApi(id: LivedocID, state: State): ?ApiDoc {
  return state.livedoc && getElementById(id, state.livedoc.apis);
}

export function getType(id: LivedocID, state: State): ?ApiObjectDoc {
  return state.livedoc && getElementById(id, state.livedoc.objects);
}

export function getFlow(id: LivedocID, state: State): ?ApiFlowDoc {
  return state.livedoc && getElementById(id, state.livedoc.flows);
}

export function isDocLoaded(state: State): boolean {
  return state.livedoc !== null;
}