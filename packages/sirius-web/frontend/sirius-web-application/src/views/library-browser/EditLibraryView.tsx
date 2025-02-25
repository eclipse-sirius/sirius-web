/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { SelectionContextProvider, Workbench } from '@eclipse-sirius/sirius-components-core';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { makeStyles } from 'tss-react/mui';
import { EditProjectNavbar } from '../edit-project/EditProjectNavbar/EditProjectNavbar';
import { EditLibraryViewParams, EditLibraryViewState } from './EditLibraryView.types';
import { useLibrary } from './useLibrary';

const useEditLibraryViewStyles = makeStyles()(() => ({
  editProjectView: {
    display: 'grid',
    gridTemplateRows: 'min-content min-content minmax(0, 1fr)',
    gridTemplateColumns: '1fr',
    height: '100vh',
    width: '100vw',
  },
}));

export const EditLibraryView = () => {
  const params = useParams<EditLibraryViewParams>();
  const { classes } = useEditLibraryViewStyles();
  const [state, setState] = useState<EditLibraryViewState>({
    data: null,
  });

  const { data } = useLibrary(params);

  useEffect(() => {
    if (data) {
      setState((prevState) => ({ ...prevState, data }));
    }
  }, [data]);

  return (
    <div className={classes.editProjectView}>
      {!!state.data ? (
        <SelectionContextProvider initialSelection={{ entries: [] }}>
          <EditProjectNavbar readOnly={true} />
          <Workbench
            editingContextId={state.data.viewer.library.currentEditingContext.id}
            initialRepresentationSelected={null}
            onRepresentationSelected={null}
            readOnly={true}
          />
        </SelectionContextProvider>
      ) : null}
      ;
    </div>
  );
};
