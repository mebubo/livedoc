// @flow
import * as React from 'react';
import { Route } from 'react-router-dom';
import TypeTemplate from './TypeExample';

type SidePanelProps = {}

export const SidePanel = (props: SidePanelProps) => <Route path="/types/:typeId" component={TypeTemplate}/>;
