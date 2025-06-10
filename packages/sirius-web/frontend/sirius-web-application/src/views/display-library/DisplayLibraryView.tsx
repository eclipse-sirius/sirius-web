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
import { RepresentationPathContext, SelectionContextProvider, Workbench } from '@eclipse-sirius/sirius-components-core';
import { Navigate, useParams } from 'react-router-dom';
import { makeStyles } from 'tss-react/mui';
import { NavigationBar } from '../../navigationBar/NavigationBar';
import { DisplayLibraryNavbar } from './DisplayLibraryNavbar';
import { DisplayLibraryViewParams } from './DisplayLibraryView.types';
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

  const getRepresentationPath = (representationId: string) => {
    // Note that this should match the corresponding route configuration
    return `/libraries/${namespace}/${name}/${version}/edit/${representationId}`;
  };

  if (data && !data.viewer.library) {
    return <Navigate to="/errors/404" replace />;
  }

  return (
    <div className={classes.displayLibraryView}>
      {loading ? <NavigationBar /> : null}
      {data && data.viewer.library ? (
        <RepresentationPathContext.Provider value={{ getRepresentationPath }}>
          <SelectionContextProvider initialSelection={{ entries: [] }}>
            <DisplayLibraryNavbar library={data.viewer.library} />
            <Workbench
              editingContextId={data.viewer.library.currentEditingContext.id}
              initialRepresentationSelected={null}
              onRepresentationSelected={null}
              readOnly
            />
          </SelectionContextProvider>
        </RepresentationPathContext.Provider>
      ) : null}
      ;
    </div>
  );
};
