/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { ApolloProvider } from "@apollo/client";
import {
  SelectionContextProvider,
  ServerContext,
} from "@eclipse-sirius/sirius-components-core";
import {
  DiagramRepresentation,
  GQLDiagram,
  WorkbenchDiagramRepresentationHandle,
} from "@eclipse-sirius/sirius-components-diagrams";
import "@xyflow/react/dist/style.css";
import { LayoutOptions } from "elkjs/lib/elk-api";
import i18n from "i18next";
import { useEffect, useMemo, useRef } from "react";
import { I18nextProvider, initReactI18next } from "react-i18next";
import "./global.css";
import { DiagramStoryArgs } from "./layoutConfigurations";
import { useClient } from "./useClient";

if (!i18n.isInitialized) {
  i18n.use(initReactI18next).init({});
}

export const DiagramStoryWrapper = ({
  args,
  diagram,
  layoutOptions,
}: {
  args: DiagramStoryArgs;
  diagram: () => GQLDiagram;
  layoutOptions: LayoutOptions;
}) => {
  const diagramHandleRef = useRef<WorkbenchDiagramRepresentationHandle>(null);
  const initialDiagram = useMemo(() => diagram(), []);
  const client = useClient(initialDiagram);

  useEffect(() => {
    if (!args.autoLayout) return;
    setTimeout(() => {
      if (diagramHandleRef.current?.applyLayout) {
        diagramHandleRef.current.applyLayout(layoutOptions);
      }
    }, 1000);
  }, [args.autoLayout, layoutOptions]);

  return (
    <ApolloProvider client={client}>
      <I18nextProvider i18n={i18n}>
        <ServerContext.Provider value={{ httpOrigin: "http://localhost" }}>
          <SelectionContextProvider initialSelection={{ entries: [] }}>
            <div
              style={{ position: "relative", width: "100%", height: "100%" }}
            >
              <div className={"diagramRepresentationContainer"}>
                <DiagramRepresentation {...args} ref={diagramHandleRef} />
              </div>
            </div>
          </SelectionContextProvider>
        </ServerContext.Provider>
      </I18nextProvider>
    </ApolloProvider>
  );
};
