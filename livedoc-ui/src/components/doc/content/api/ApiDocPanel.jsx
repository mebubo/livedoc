// @flow
import * as React from 'react';
import { Accordion } from 'react-bootstrap';
import type { ApiDoc } from '../../../../model/livedoc';
import { ApiMethodPanel } from './ApiMethodPanel';
import { ContentHeader } from '../ContentHeader';

export type ApiDocPanelProps = {
  apiDoc: ApiDoc,
  onMethodSelect: (id: string) => void,
  onTypeClick: (id: string) => void,
}

export const ApiDocPanel = (props: ApiDocPanelProps) => {
  const api: ApiDoc = props.apiDoc;

  const methodPanels = api.methods.map(m => <ApiMethodPanel key={m.id} methodDoc={m} onTypeClick={props.onTypeClick}/>);

  return <section>
    <ContentHeader title={api.name} description={api.description}/>
    <Accordion onSelect={props.onMethodSelect}>
      {methodPanels}
    </Accordion>
  </section>;
};
