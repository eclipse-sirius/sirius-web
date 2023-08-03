/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/

import { Selection, SelectionContextProvider } from '@eclipse-sirius/sirius-components-core';
import { DiagramRepresentation } from '@eclipse-sirius/sirius-components-diagrams';
import { DiagramRepresentation as ReactFlowDiagramRepresentation } from '@eclipse-sirius/sirius-components-diagrams-reactflow';
import { DetailsView, FormRepresentation } from '@eclipse-sirius/sirius-components-forms';
import React, { useEffect, useState } from 'react';
import './Sprotty.css';
import './reset.css';
import './variables.css';

interface AppState {
  editingContextId: string;
  representationId: string;
  representationLabel: string;

  authenticate: boolean;
}

interface AppProps {
  serverAddress: string;
  username: string;
  password: string;
  editingContextId: string;
  representationId: string;
  representationLabel: string;
  representationKind: string;
}

export const App = ({
  serverAddress,
  username,
  password,
  editingContextId,
  representationId,
  representationLabel,
  representationKind,
}: AppProps) => {
  const [state, setState] = useState<AppState>({
    editingContextId,
    representationId,
    representationLabel,
    authenticate: false,
  });

  useEffect(() => {
    setState((prevState) => {
      return { ...prevState, editingContextId, representationId };
    });
  }, [editingContextId, representationId]);

  const appStyle = {
    display: 'grid',
    gridTemplateColumns: '1fr',
    gridTemplateRows: 'min-content 1fr',
    rowGap: '16px',
    height: '1000px',
    width: '1000px',
    padding: '32px',
  };

  const headerStyle = {
    display: 'grid',
    gridTemplateColumns: 'min-content min-content min-content min-content min-content',
    gridTemplateRows: '1fr',
    columnGap: '16px',
  };

  const componentStyle = {
    display: 'grid',
    gridTemplateColumns: '1fr',
    gridTemplateRows: '1fr',
  };

  useEffect(() => {
    if (username && password) {
      fetch(`${serverAddress}/api/authenticate`, {
        method: 'POST',
        credentials: 'include',
        mode: 'no-cors',
        body: new URLSearchParams({ username, password, 'remember-me': 'true' }),
      })
        .then(() => {
          setState((prevState) => {
            return { ...prevState, authenticate: true };
          });
        })
        .catch(() => {});
    } else {
      setState((prevState) => {
        return { ...prevState, authenticate: true };
      });
    }
  }, []);

  let component;
  let selection: Selection = { entries: [] };
  if (
    representationKind.startsWith('siriusComponents://representation?type=Diagram') &&
    representationLabel.endsWith('__REACT_FLOW')
  ) {
    component = (
      <ReactFlowDiagramRepresentation
        editingContextId={state.editingContextId}
        representationId={state.representationId}
        readOnly={false}
      />
    );
  } else if (representationKind.startsWith('siriusComponents://representation?type=Diagram')) {
    component = (
      <DiagramRepresentation
        editingContextId={state.editingContextId}
        representationId={state.representationId}
        readOnly={false}
      />
    );
  } else if (representationKind.startsWith('siriusComponents://representation?type=Form')) {
    component = (
      <FormRepresentation
        editingContextId={state.editingContextId}
        representationId={state.representationId}
        readOnly={false}
      />
    );
  } else {
    selection = {
      entries: [{ id: state.representationId, label: state.representationLabel, kind: representationKind }],
    };
    component = <DetailsView editingContextId={state.editingContextId} readOnly={false} />;
  }
  return (
    <SelectionContextProvider initialSelection={selection}>
      <div style={appStyle}>
        <div style={headerStyle}></div>
        {state.editingContextId && state.authenticate ? <div style={componentStyle}>{component}</div> : null}
      </div>
    </SelectionContextProvider>
  );
};
