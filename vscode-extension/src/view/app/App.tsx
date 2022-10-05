/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

import { Selection } from '@eclipse-sirius/sirius-components-core';
import { DiagramRepresentation } from '@eclipse-sirius/sirius-components-diagrams';
import { DetailsView } from '@eclipse-sirius/sirius-components-forms';
import React, { useEffect, useState } from 'react';
import './reset.css';
import './Sprotty.css';
import './variables.css';

interface AppState {
  editingContextId: string;
  representationId: string;
  representationLabel: string;
  selection: Selection;
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
    selection: { entries: [] },
    authenticate: false,
  });

  useEffect(() => {
    setState((prevState) => {
      return { ...prevState, editingContextId, representationId };
    });
  }, [editingContextId, representationId]);

  const setSelection = (selection: Selection) => {
    setState((prevState) => {
      return { ...prevState, selection };
    });
  };

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
  if (representationKind.startsWith('siriusComponents://representation?type=Diagram')) {
    component = (
      <DiagramRepresentation
        editingContextId={state.editingContextId}
        representationId={state.representationId}
        readOnly={false}
        selection={state.selection}
        setSelection={setSelection}
      />
    );
  } else {
    const propertiesSelection: Selection = {
      entries: [{ id: state.representationId, label: state.representationLabel, kind: representationKind }],
    };

    component = (
      <DetailsView
        editingContextId={state.editingContextId}
        readOnly={false}
        selection={propertiesSelection}
        setSelection={() => {}}
      />
    );
  }
  return (
    <div style={appStyle}>
      <div style={headerStyle}></div>
      {state.editingContextId && state.authenticate ? <div style={componentStyle}>{component}</div> : null}
    </div>
  );
};
