/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
import {
  RepresentationMetadata,
  RepresentationPathContext,
  SelectionContextProvider,
  Workbench,
} from '@eclipse-sirius/sirius-components-core';
import { useState } from 'react';
import { Navigate, useParams } from 'react-router-dom';
import { makeStyles } from 'tss-react/mui';
import { NavigationBar } from '../../navigationBar/NavigationBar';
import { useInitialWorkbenchConfiguration } from '../edit-project/useInitialWorkbenchConfiguration';
import { DisplayLibraryNavbar } from './DisplayLibraryNavbar';
import { DisplayLibraryViewParams, DisplayLibraryViewState } from './DisplayLibraryView.types';
import { LibraryContext } from './LibraryContext';
import { useLibrary } from './useLibrary';

const useDisplayLibraryViewStyles = makeStyles()(() => ({
  displayLibraryView: {
    display: 'grid',
    gridTemplateRows: 'min-content minmax(0, 1fr)',
    gridTemplateColumns: '1fr',
    height: '100vh',
    width: '100vw',
  },
}));

export const DisplayLibraryView = () => {
  const { namespace, name, version } = useParams<DisplayLibraryViewParams>();
  const { classes } = useDisplayLibraryViewStyles();
  const { data, loading } = useLibrary(
    decodeURIComponent(namespace ?? ''),
    decodeURIComponent(name ?? ''),
    decodeURIComponent(version ?? '')
  );
  const { workbenchConfiguration } = useInitialWorkbenchConfiguration(
    data?.viewer?.library ? data.viewer.library.currentEditingContext.id : null
  );

  const [state, setState] = useState<DisplayLibraryViewState>({
    representation: null,
  });

  const getRepresentationPath = (representationId: string) => {
    // Note that this should match the corresponding route configuration
    return `/libraries/${namespace}/${name}/${version}/edit/${representationId}`;
  };

  const onRepresentationSelected = (representationMetadata: RepresentationMetadata) => {
    setState((prevState) => ({
      ...prevState,
      representation: representationMetadata,
    }));
  };

  if (data && !data.viewer.library) {
    return <Navigate to="/errors/404" replace />;
  }

  return (
    <div className={classes.displayLibraryView}>
      {loading ? <NavigationBar /> : null}
      {data && workbenchConfiguration ? (
        <LibraryContext.Provider value={{ library: data.viewer.library }}>
          <RepresentationPathContext.Provider value={{ getRepresentationPath }}>
            <SelectionContextProvider initialSelection={{ entries: [] }}>
              <DisplayLibraryNavbar />
              <Workbench
                editingContextId={data.viewer.library.currentEditingContext.id}
                initialRepresentationSelected={state.representation}
                onRepresentationSelected={onRepresentationSelected}
                readOnly
                initialWorkbenchConfiguration={workbenchConfiguration}
                ref={null}
              />
            </SelectionContextProvider>
          </RepresentationPathContext.Provider>
        </LibraryContext.Provider>
      ) : null}
      ;
    </div>
  );
};
